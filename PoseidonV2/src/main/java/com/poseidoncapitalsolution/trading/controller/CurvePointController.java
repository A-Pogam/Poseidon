package com.poseidoncapitalsolution.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.poseidoncapitalsolution.trading.model.CurvePoint;
import com.poseidoncapitalsolution.trading.service.contracts.ICurvePointService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/curvePoint")
public class CurvePointController {

	@Autowired
	private ICurvePointService curvePointService;

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("curvePoints", curvePointService.findAll());
		return "curvePoint/list";
	}

	@GetMapping("/add")
	public String addForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	@PostMapping("/add")
	public String add(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}
		curvePointService.save(curvePoint);
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint curvePoint = curvePointService.findById(id);
		if (curvePoint == null) {
			throw new IllegalArgumentException("Invalid curvePoint Id:" + id);
		}
		model.addAttribute("curvePoint", curvePoint);
		return "curvePoint/update";
	}

	@PutMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			curvePoint.setId(id);
			return "curvePoint/update";
		}
		curvePointService.update(id, curvePoint);
		return "redirect:/curvePoint/list";
	}

	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id, Model model) {
		curvePointService.deleteById(id);
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/findByCurveId/{curveId}")
	public String findByCurveId(@PathVariable("curveId") Integer curveId, Model model) {
		model.addAttribute("curvePoints", curvePointService.findByCurveId(curveId));
		return "curvePoint/list";
	}
}
