package com.sduept.dragon.manager.webservice;

import com.sduept.dragon.manager.entity.Note;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface WebServiceDemoService {

    String checkMachineCodeExpireById(@WebParam(name = "code")Integer id);

    @WebMethod
    String checkMachineCodeExpire(@WebParam(name = "code") String code);

    /**
     * 获取所有的客户
     */
    @WebMethod
    String listAllCustomers();

    /**
     * 新增客户
     *
     * @param customerJson json类型的客户数据
     */
    @WebMethod
    String addCustomer(@WebParam(name = "customer") String customerJson);

    /**
     * 删除客户
     *
     * @param id 客户ID
     */
    @WebMethod
    String deleteCustomer(@WebParam(name = "id") Integer id);

    /**
     * 更新客户信息
     *
     * @param customerJson json类型的客户数据
     */
    @WebMethod
    String updateCustomer(@WebParam(name = "customer") String customerJson);

    /**
     * 获取所有的机器码
     */
    @WebMethod
    String listAllMachineCodes();

    /**
     * 获取指定客户的机器码
     *
     * @param customerId 客户ID
     */
    @WebMethod
    String listMachineCodesByCustomerId(@WebParam(name = "customerId") Integer customerId);

    /**
     * 新增机器码
     *
     * @param machineCode json类型的机器码数据
     */
    @WebMethod
    String addMachineCode(@WebParam(name = "machineCode") String machineCode);

    /**
     * 更新机器码
     *
     * @param machineCode json类型的机器码数据
     */
    @WebMethod
    String updateMachineCode(@WebParam(name = "machineCode") String machineCode);

    /**
     * 根据机器码ID删除机器码
     *
     * @param id 机器码ID
     */
    @WebMethod
    String deleteMachineCode(@WebParam(name = "id") Integer id);

    @WebMethod
    String addNote(Note note);

    @WebMethod
    String editNote(Note note);

    @WebMethod
    String deleteNote(Integer id);

    @WebMethod
    String listNotes();

    @WebMethod
    String listAdList();

    @WebMethod
    String getOneTitle(@WebParam(name = "titleName") String titleName);

    @WebMethod
    byte[] downloadFile(String path);
}
