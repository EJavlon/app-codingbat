package com.codingbat.appcodingbat.service;

import com.codingbat.appcodingbat.entity.Modul;
import com.codingbat.appcodingbat.entity.ProgrammingLanguage;
import com.codingbat.appcodingbat.payload.ApiResponse;
import com.codingbat.appcodingbat.payload.LanguageDto;
import com.codingbat.appcodingbat.repository.ModulRepository;
import com.codingbat.appcodingbat.repository.ProgrammingLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProgrammingLanguageService {
    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;
    @Autowired
    private ModulRepository modulRepository;

    public List<ProgrammingLanguage> getProgrammingLanguages() {
        return programmingLanguageRepository.findAll();
    }

    public ProgrammingLanguage getProgrammingLanguage(Integer id) {
        return programmingLanguageRepository.findById(id).orElse(null);
    }

    public ApiResponse addProgrammingLanguage(LanguageDto languageDto){
        boolean exists = programmingLanguageRepository.existsByName(languageDto.getName());
        if (exists) return new ApiResponse("There is such a programming language",false);

        ProgrammingLanguage language = new ProgrammingLanguage();
        language.setName(languageDto.getName());
        language.setModuls(new HashSet<>());
        language.setActive(languageDto.isActive());
        programmingLanguageRepository.save(language);

        return new ApiResponse("Language seccessfully saved",true);
    }

    public ApiResponse editProgrammingLanguage(Integer id, LanguageDto languageDto) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(id);
        if (!optionalProgrammingLanguage.isPresent())
            return new ApiResponse("Language not found",false);

        boolean exists = programmingLanguageRepository.existsByNameAndIdNot(languageDto.getName(), id);
        if (exists) return new ApiResponse("There is such a programming language",false);

        ProgrammingLanguage language = optionalProgrammingLanguage.get();
        language.setName(languageDto.getName());
        language.setActive(languageDto.isActive());
        programmingLanguageRepository.save(language);

        return new ApiResponse("Language seccessfully edited",true);
    }

    public ApiResponse deleteProgrammingLanguage(Integer id) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(id);
        if (!optionalProgrammingLanguage.isPresent())
            return new ApiResponse("Language not found",false);

        programmingLanguageRepository.delete(optionalProgrammingLanguage.get());
        return new ApiResponse("Language seccessfully deleted",true);
    }

    public Set<Modul> getProgrammingLanguageModuls(Integer languageId) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(languageId);
        return optionalProgrammingLanguage.map(ProgrammingLanguage::getModuls).orElse(null);

    }

    public Modul getProgrammingLanguageModul(Integer languageId, Integer modulId) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(languageId);
        if (!optionalProgrammingLanguage.isPresent()) return null;

        ProgrammingLanguage language = optionalProgrammingLanguage.get();
        Set<Modul> moduls = language.getModuls();

        for (Modul modul : moduls)
            if (modul.getId().equals(modulId))
                return modul;

        return null;
    }

    public ApiResponse addModulToLanguage(Integer languageId, Integer modulId) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(languageId);
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("Programming language not found",false);

        Optional<Modul> optionalModul = modulRepository.findById(modulId);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found",false);

        ProgrammingLanguage language = optionalProgrammingLanguage.get();
        Set<Modul> moduls = language.getModuls();
        moduls.add(optionalModul.get());
        language.setModuls(moduls);

        programmingLanguageRepository.save(language);
        return new ApiResponse("Modul seccessfully saved",true);
    }

    public ApiResponse editLanguageModul(Integer languageId, Integer modulId, Integer newModulId) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(languageId);
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("Programming language not found",false);

        Optional<Modul> optionalModul = modulRepository.findById(modulId);
        if (!optionalModul.isPresent()) return new ApiResponse("Old modul not found",false);

        Optional<Modul> optionalNewModul = modulRepository.findById(newModulId);
        if (!optionalNewModul.isPresent()) return new ApiResponse("New modul not found",false);

        ProgrammingLanguage language = optionalProgrammingLanguage.get();
        Set<Modul> moduls = language.getModuls();
        moduls.remove(optionalModul.get());
        moduls.add(optionalNewModul.get());
        language.setModuls(moduls);
        programmingLanguageRepository.save(language);

        return new ApiResponse("Modul seccessfully edited",true);
    }

    public ApiResponse deleteLanguageModul(Integer languageId, Integer modulId) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(languageId);
        if (!optionalProgrammingLanguage.isPresent()) return new ApiResponse("Programming language not found",false);

        Optional<Modul> optionalModul = modulRepository.findById(modulId);
        if (!optionalModul.isPresent()) return new ApiResponse("Modul not found",false);

        ProgrammingLanguage language = optionalProgrammingLanguage.get();
        Set<Modul> moduls = language.getModuls();
        moduls.remove(optionalModul.get());
        language.setModuls(moduls);
        programmingLanguageRepository.save(language);

        return new ApiResponse("Modul seccessfully deleted",true);
    }
}
