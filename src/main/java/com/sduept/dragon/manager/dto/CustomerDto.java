package com.sduept.dragon.manager.dto;

import com.sduept.dragon.manager.entity.Customer;
import com.sduept.dragon.manager.entity.MachineCode;

import java.util.List;

public class CustomerDto extends Customer {

    private List<MachineCode> machineCodes;

    public List<MachineCode> getMachineCodes() {
        return machineCodes;
    }

    public void setMachineCodes(List<MachineCode> machineCodes) {
        this.machineCodes = machineCodes;
    }
}
