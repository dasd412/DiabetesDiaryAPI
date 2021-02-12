package com.dasd412.domain.diary;

import com.dasd412.domain.user.Email;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Profile("test")
public class WriterRepositoryTest {

    @Autowired
    private WriterRepository writerRepository;


    @After
    public void cleanUp(){
        writerRepository.deleteAll();
    }

    @Test
    public void 이메일을_저장한다(){
        Email email=new Email("dasd412@naver.com");

        Writer writer=new Writer("tester",email);

        writerRepository.save(writer);

        List<Writer>writerList=writerRepository.findAll();
        assertThat(writerList.get(0).getName()).isEqualTo(writer.getName());
        assertThat(writerList.get(0).getEmail()).isEqualTo(writer.getEmail());

    }
}