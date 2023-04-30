package ru.vovai.naumentesttask;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.vovai.naumentesttask.repository.NameAgeRepository;
import ru.vovai.naumentesttask.repository.impl.NameAgeRepositoryImpl;

import java.io.IOException;

@SpringBootTest
@TestPropertySource(properties = {
        "name.data=hello",
})
public class RepositoryTest {

    private final NameAgeRepository nameAgeRepository = new NameAgeRepositoryImpl(fileCommunicator);

    @Test
    public void getName() {
        System.out.println(nameAgeRepository.findByName("Vova"));
    }

    @Test
    public void getAllNames() throws IOException {
        System.out.println(nameAgeRepository.findAll());
    }

}
