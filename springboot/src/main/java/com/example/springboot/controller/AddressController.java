package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Address;
import com.example.springboot.service.AddressService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Resource
	private AddressService addressService;

	@PostMapping("/add")
	public Result add(@RequestBody Address address){
		addressService.add(address);
		return Result.success();
	}

	@DeleteMapping("/deleteById")
	public Result deleteById(@RequestParam Integer id){
		addressService.delete(id);
		return Result.success();
	}
	@DeleteMapping("/deleteBatch")
	public Result deleteBatch(@RequestBody List<Integer> ids){
		addressService.deleteBatch(ids);
		return Result.success();
	}

	@PutMapping("/update")
	public Result update(@RequestBody Address address){
		addressService.update(address);
		return Result.success();
	}


	@GetMapping("/selectById/{id}")
	public Result selectById(@PathVariable Integer id){
		Address address=addressService.selectById(id);
		return Result.success(address);
	}

	@GetMapping("/selectAll")
	public Result selectAll(Address address){
		//传参数通过typeName用来进行模糊查询
		//当typeName为null时候，进行的就是查询全部
		List<Address> list=addressService.selectAll(address);
		return Result.success(list);
	}

	//分页查询
	@GetMapping("/selectPage")
	public Result selectPage(Address address,
	                         @RequestParam(defaultValue = "1") Integer pageNum,
	                         @RequestParam(defaultValue = "10") Integer pageSize){
		PageInfo<Address> addressPageInfo = addressService.selectPage(address,pageNum, pageSize);
		return Result.success(addressPageInfo);
	}

}
