package com.sduept.dragon.manager.webservice.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sduept.dragon.manager.common.ServerResponse;
import com.sduept.dragon.manager.dto.CustomerDto;
import com.sduept.dragon.manager.dto.MachineCodeDto;
import com.sduept.dragon.manager.entity.Customer;
import com.sduept.dragon.manager.entity.MachineCode;
import com.sduept.dragon.manager.entity.Note;
import com.sduept.dragon.manager.service.CustomerService;
import com.sduept.dragon.manager.service.MachineCodeService;
import com.sduept.dragon.manager.service.NoteService;
import com.sduept.dragon.manager.webservice.WebServiceDemoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@WebService(serviceName = "WebServiceDemoService",
        targetNamespace = "http://mananger.dragon.sduept.com",
        endpointInterface = "com.sduept.dragon.manager.webservice.WebServiceDemoService"
)
@Slf4j
public class WebServiceDemoServiceImpl implements WebServiceDemoService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MachineCodeService machineCodeService;

    @Autowired
    private NoteService noteService;

    @Override
    public String checkMachineCodeExpireById(Integer id) {
        MachineCode machineCode = machineCodeService.getOne(new QueryWrapper<MachineCode>()
                .lambda().eq(MachineCode::getId, id).eq(MachineCode::getDeleted, false));
        long value = machineCode.getExpireTime().getTime() - DateTime.now().getMillis();
        if (value < 0) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码已经过期"));
        }
        return JSON.toJSONString(ServerResponse.createBySuccessMessage("机器码可正常使用"));
    }

    @Override
    public String checkMachineCodeExpire(String code) {
        log.info("接收的code=" + code);
        MachineCode machineCode = machineCodeService.getOne(new QueryWrapper<MachineCode>()
                .lambda().eq(MachineCode::getCode, code).eq(MachineCode::getDeleted, false));
        long value = machineCode.getExpireTime().getTime() - DateTime.now().getMillis();
        if (value < 0) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码已经过期"));
        }
        return JSON.toJSONString(ServerResponse.createBySuccessMessage("机器码可正常使用"));
    }

    @Override
    public String listAllCustomers() {
        List<Customer> customers = customerService.list(new QueryWrapper<Customer>()
                .lambda().eq(Customer::getDeleted, false));
        if (customers.isEmpty()) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("客户不存在"));
        }
        return JSON.toJSONString(ServerResponse.createBySuccess(customers));
    }

    @Transactional
    @Override
    public String addCustomer(String customerJson) {
        if (StringUtils.isBlank(customerJson)) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("客户数据为空"));
        }

        CustomerDto customerDto = JSONObject.parseObject(customerJson, CustomerDto.class);

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        customer.setUpdateTime(new Date());
        boolean isCustomerSaved = customerService.save(customer);
        if (isCustomerSaved && !Objects.isNull(customerDto.getMachineCodes())) {
            customerDto.getMachineCodes().stream().forEach(item -> {
                item.setUpdateTime(new Date());
                item.setCustomerId(customer.getId());
            });
            machineCodeService.saveBatch(customerDto.getMachineCodes());
        } else {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("客户新增失败"));
        }
        return JSON.toJSONString(ServerResponse.createBySuccess("客户新增成功"));
    }

    @Override
    public String deleteCustomer(Integer id) {
        if (Objects.isNull(id)) {
            JSON.toJSONString(ServerResponse.createByErrorMessage("客户ID不能为空"));
        }

        Customer customer = new Customer();
        customer.setId(id);
        customer.setDeleted(true);
        boolean isDeleted = customerService.updateById(customer);
        if (isDeleted) {
            MachineCode machineCode = new MachineCode();
            machineCode.setDeleted(true);
            machineCodeService.update(machineCode, new QueryWrapper<MachineCode>().lambda().eq(MachineCode::getCustomerId, id));
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("客户删除成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("客户删除失败"));
    }

    @Override
    public String updateCustomer(String customerJson) {
        if (StringUtils.isBlank(customerJson)) {
            JSON.toJSONString(ServerResponse.createByErrorMessage("客户数据为空"));
        }

        CustomerDto customerDto = JSON.parseObject(customerJson, CustomerDto.class);

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        boolean isSuccessed = customerService.updateById(customer);
        if (isSuccessed && !Objects.isNull(customerDto.getMachineCodes())) {
            customerDto.getMachineCodes().stream().forEach(item -> item.setUpdateTime(new Date()));
            machineCodeService.updateBatchById(customerDto.getMachineCodes());
        } else {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("用户更新失败"));
        }
        return JSON.toJSONString(ServerResponse.createBySuccessMessage("用户更新成功"));
    }

    @Override
    public String listAllMachineCodes() {
        List<MachineCode> machineCodes = machineCodeService.list(new QueryWrapper<MachineCode>().lambda()
                .eq(MachineCode::getDeleted, false));
        return JSON.toJSONString(ServerResponse.createBySuccess(machineCodes));
    }

    @Override
    public String listMachineCodesByCustomerId(Integer customerId) {
        List<MachineCode> machineCodes = machineCodeService.list(new QueryWrapper<MachineCode>().lambda()
                .eq(MachineCode::getCustomerId, customerId).eq(MachineCode::getDeleted, false));
        return JSON.toJSONString(ServerResponse.createBySuccess(machineCodes));
    }

    @Override
    public String addMachineCode(String machineCodeJson) {
        if (StringUtils.isBlank(machineCodeJson)) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码数据为空"));
        }
        MachineCodeDto machineCodeDto = JSON.parseObject(machineCodeJson, MachineCodeDto.class);
        machineCodeDto.getMachineCodes().stream().forEach(item -> {
            item.setCustomerId(machineCodeDto.getCustomerId());
            item.setUpdateTime(new Date());
        });

        boolean isSuccessed = machineCodeService.saveBatch(machineCodeDto.getMachineCodes());
        if (isSuccessed) {
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("机器码添加成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码添加失败"));
    }

    @Override
    public String updateMachineCode(String machineCodeJson) {
        if (StringUtils.isBlank(machineCodeJson)) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码数据为空"));
        }
        MachineCode machineCode = JSON.parseObject(machineCodeJson, MachineCode.class);
        boolean isSuccessed = machineCodeService.updateById(machineCode);
        if (isSuccessed) {
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("机器码修改成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码修改失败"));
    }

    @Override
    public String deleteMachineCode(Integer id) {
        MachineCode machineCode = new MachineCode();
        machineCode.setDeleted(true);
        boolean isSuccessed = machineCodeService.update(machineCode, new QueryWrapper<MachineCode>()
                .lambda().eq(MachineCode::getId, id));
        if (isSuccessed) {
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("机器码删除成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("机器码删除失败"));
    }

    @Override
    public String addNote(Note note) {
        if (Objects.isNull(note)) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("请传入正确的数据"));
        }
        if (noteService.save(note)) {
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("数据保存成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("数据保存失败"));
    }

    @Override
    public String editNote(Note note) {
        if (Objects.isNull(note)) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("请传入正确的数据"));
        }
        if (noteService.updateById(note)) {
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("数据更新成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("数据更新失败"));
    }

    @Override
    public String deleteNote(Integer id) {
        if (Objects.isNull(id)) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("请传入正确的数据"));
        }
        if (noteService.removeById(id)) {
            return JSON.toJSONString(ServerResponse.createBySuccessMessage("数据删除成功"));
        }
        return JSON.toJSONString(ServerResponse.createByErrorMessage("数据删除失败"));
    }

    @Override
    public String listNotes() {
        return JSON.toJSONString(ServerResponse.createBySuccess(noteService.list()));
    }

    @Override
    public String listAdList() {
        File file = new File(System.getProperty("user.dir") + File.separator + "title");
        if (!file.exists()) {
            return JSON.toJSONString(ServerResponse.createByErrorMessage("广告的配置目录不存在"));
        }

        List<String> titles = Arrays.stream(file.listFiles()).filter(f -> f.isDirectory()).map(f -> f.getName()).collect(Collectors.toList());
        return JSON.toJSONString(ServerResponse.createBySuccess(titles));
    }

    @Override
    public String getOneTitle(String titleName) {
        String path = System.getProperty("user.dir") + File.separator + "title" + File.separator + titleName;
        List<Map<String, Object>> datas = Arrays.stream(new File(path).listFiles()).filter(f -> !f.isDirectory()).map(f -> {
            Map<String, Object> result = Maps.newHashMap();
            if (f.getName().indexOf(".txt") != -1) {
                result.put("type", 0);
                result.put("content", readFileString(f));
            } else {
                result.put("type", 1);
                result.put("content", f.getAbsolutePath());
            }
            return result;
        }).collect(Collectors.toList());
        return JSON.toJSONString(ServerResponse.createBySuccess(datas));
    }

    @Override
    public byte[] downloadFile(String path) {
        File file = new File(path);
        byte[] bytes = FileUtil.readBytes(file);
        return bytes;
    }

    private String readFileString(File file) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                content.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
