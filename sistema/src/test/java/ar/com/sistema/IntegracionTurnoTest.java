package ar.com.sistema;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class IntegracionTurnoTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registrarTurno() throws Exception {

        Random rand = new Random();
        int hora = rand.nextInt(10, 23);
        int minuto = rand.nextInt(10, 59);

        String json = "{\"usuario\": {\"id\": 1},\"prestador\": {\"id\": 1},\"fechaHora\": \"2022-08-12T" + hora + ":" + minuto + ":00\"}";

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void registrarTurnoRepetido() throws Exception {

        Random rand = new Random();
        int hora = rand.nextInt(10, 23);
        int minuto = rand.nextInt(10,59);



        String json = "{\"usuario\": {\"id\": 1},\"prestador\": {\"id\": 1},\"fechaHora\": \"2022-08-12T" + hora + ":" + minuto + ":00\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/turnos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(409)).andReturn();

        Assert.assertEquals("\"El prestador con Id 1 ya tiene un turno asignado en ese horario.\"", response.getResponse().getContentAsString());
    }

    @Test
    public void listarOdontologo() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/turnos")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        Assert.assertFalse(response.getResponse().getContentAsString().isEmpty());
    }
}
