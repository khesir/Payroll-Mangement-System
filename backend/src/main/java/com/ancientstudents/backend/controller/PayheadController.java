package com.ancientstudents.backend.controller;

import com.ancientstudents.backend.exception.AddEarningsNotFoundException;
import com.ancientstudents.backend.model.Payhead;
import com.ancientstudents.backend.repository.PayheadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin("http://localhost:5175/")
@RestController
public class PayheadController {
     
    @Autowired
    private PayheadRepository payheadRepository;
     
    @PostMapping("/payhead")
    Payhead newAddEarnings(@RequestBody Payhead newAddEarnings){
        if(newAddEarnings == null) return null;
        newAddEarnings.setCreatedAt(new Date());
        newAddEarnings.setLastUpdated(new Date());
        return payheadRepository.save(newAddEarnings);
    }
    
    @GetMapping("/payhead")
    List<Payhead> getAllAddEarningss(){
        return payheadRepository.findAll();
    }

    @GetMapping("/payhead/{id}")
    Payhead getAddEarningsById(@PathVariable Long id){
        if(id == null) return null;
        return payheadRepository.findById(id)
                .orElseThrow(()->new AddEarningsNotFoundException(id));
    }
     
    @PutMapping("payhead/{id}")
    Payhead updateAddEarnings(@RequestBody Payhead newPayhead, @PathVariable Long id){
        if(id == null) return null;
        return payheadRepository.findById(id)
                .map(payhead -> {
                    payhead.setName(newPayhead.getName());
                    payhead.setAmount(newPayhead.getAmount());
                    payhead.setCreatedAt(newPayhead.getCreatedAt());
                    payhead.setLastUpdated(new Date());
                    return payheadRepository.save(newPayhead);
                }).orElseThrow(()->new AddEarningsNotFoundException(id));
    }
     
    @DeleteMapping("payhead/{id}")
    String deleteAddEarnings(@PathVariable Long id){
        if(id == null) return null;
        if(!payheadRepository.existsById(id)){
            throw new AddEarningsNotFoundException(id);
        }
        payheadRepository.deleteById(id);
        return "payhead with id " + id + " has been deleted successfully.";
    }
     
    // @RequestMapping(value = "payhead/top", method=RequestMethod.GET)
    // public Page<Payhead> requestMethodName(@RequestParam(value ="count") String count) {
    //     PageRequest pageRequest = PageRequest.of(0,Integer.valueOf(count));
    //     Page<Payhead> topAddEarnings = payheadRepository.findAll(pageRequest);

    //     return topAddEarnings;
    // }
    
}