package se.taekwondointernship.data.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.taekwondointernship.data.exceptions.ResourceNotFoundException;
import se.taekwondointernship.data.models.entity.Pass;
import se.taekwondointernship.data.models.form.PassForm;
import se.taekwondointernship.data.repository.PassRepository;

import java.util.List;

@Service
public class PassServiceImplementation implements PassService {
    private final ModelMapper modelMapper;
    private final PassRepository passRepository;

    @Autowired
    public PassServiceImplementation(ModelMapper modelMapper, PassRepository passRepository) {
        this.modelMapper = modelMapper;
        this.passRepository = passRepository;
    }
@Transactional
    @Override
    public List<PassForm> findAll() {
        Iterable<Pass> foundAll=passRepository.findAll();
        List<PassForm> ListOfPersons=modelMapper.map(foundAll,new TypeToken<List<PassForm>>(){}.getType());
        return ListOfPersons;
    }
@Transactional
    @Override
    public PassForm findByName(String firstName, String lastName) {
        if(firstName==null) throw new IllegalArgumentException("First Name is null");
        Pass foundName = passRepository.findByName(firstName,lastName).orElseThrow(() -> new ResourceNotFoundException("Name not found"));
        return modelMapper.map(foundName,PassForm.class);
    }
}
