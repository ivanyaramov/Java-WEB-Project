package com.example.project.service.impl;

import com.example.project.model.entity.Guide;
import com.example.project.model.service.GuideServiceModel;
import com.example.project.repository.GuideRepository;
import com.example.project.service.GuideService;
import com.example.project.web.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuideServiceImpl implements GuideService {
    private final GuideRepository guideRepository;
    private final ModelMapper modelMapper;

    public GuideServiceImpl(GuideRepository guideRepository, ModelMapper modelMapper) {
        this.guideRepository = guideRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void initaliseGuides() {
        if (guideRepository.count()==0){
            Guide anton = new Guide("Anton Biserov", 47, "https://res.cloudinary.com/ivoto22/image/upload/v1636288787/aaaaa_rpgncy.jpg");
            anton.setDescription("Hello, my name is Anton Biserov. I have been a guide in this company for 3 years. I have many years of experience in the field, as I have been doing this for 15 years in total. I speak 4 languages perfectly - Italian, Bulgarian, English and German. Travel and history have always been my passion, and sharing it with people makes me really happy. I will be happy to immerse ourselves together in the culture and history of countries and create memories for a lifetime.");
            Guide alexa = new Guide("Alexa Dimitrova", 38, "https://res.cloudinary.com/ivoto22/image/upload/v1636288028/246529621_983275065563103_8771009147949031211_n_mwdgrq.jpg");
            alexa.setDescription("Hello, I'm Alexa Dimitrova.  I have been working as a guide for 17 years, and in this agency for 2. I am Spanish by nationality, but my husband is Bulgarian and that made my connection with Bulgaria very strong. I speak 5 languages perfectly - Bulgarian, Portuguese, Spanish, English and French. From an young age I dream of working with people, and combined with travel and positive emotions it makes the perfect combination.");
            Guide teodor = new Guide("Teodor Vladimirov", 31, "https://res.cloudinary.com/ivoto22/image/upload/v1636287752/248002542_4522390877849592_4374976687453367217_n_ouxfop.jpg");
            teodor.setDescription("Hello, I'm Teodor Vladimirov.  I have been working in the field of tourism for 10 years, and in this agency for 5. I have traveled a lot with my parents. Since I was a child and I have always wanted to become a guide when I looked at the desire they work with, and who would not want such a job?  This motivated me to learn 4 languages - Bulgarian, German, Dutch, French, perfectly, to be one of the best in the field, and at the moment I am even learning 5th. I will be happy to give you the emotion of touching the antiquity of each country.");
            guideRepository.save(anton);
            guideRepository.save(alexa);
            guideRepository.save(teodor);
        }
    }

    @Override
    public Guide findByFullName(String fullName) {
        return guideRepository.findByFullName(fullName).orElse(null);
    }

    @Override
    public void createGuide(GuideServiceModel guideServiceModel) {
        Guide guide = modelMapper.map(guideServiceModel, Guide.class);
        guideRepository.save(guide);
    }

    @Override
    public GuideServiceModel findById(Long id) {
        return modelMapper.map(guideRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id,"guides")),GuideServiceModel.class);
    }

    @Override
    public Guide findGuideById(Long id) {
        return guideRepository.findById(id).orElse(null);
    }

    @Override
    public void throwExceptionIfGuideNotFound(Long id) {
        findById(id);
    }

    @Override
    public void editGuide(Long id, GuideServiceModel guideServiceModel) {
    Guide guide = findGuideById(id);
    guide.setFullName(guideServiceModel.getFullName());
    guide.setDescription(guideServiceModel.getDescription());
    guide.setAge(guideServiceModel.getAge());
    guide.setPictureUrl(guideServiceModel.getPictureUrl());
    guideRepository.save(guide);
    }

    @Override
    public List<String> getAllGuides() {
        return guideRepository.findAll().stream().map(g->g.getFullName()).collect(Collectors.toList());
    }
}
