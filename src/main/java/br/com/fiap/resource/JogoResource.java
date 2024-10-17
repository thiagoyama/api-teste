package br.com.fiap.resource;

import br.com.fiap.dao.JogoDao;
import br.com.fiap.dto.jogo.JogoCreateDto;
import br.com.fiap.dto.jogo.JogoDetailDto;
import br.com.fiap.dto.jogo.JogoUpdateDto;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Jogo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Path("/jogos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogoResource {

    private JogoDao jogoDao;
    private ModelMapper modelMapper;

    public JogoResource() throws Exception {
        jogoDao = new JogoDao(ConnectionFactory.getConnection());
        modelMapper = new ModelMapper();
    }

    @POST
    public Response cadastrar(JogoCreateDto jogoCreateDTO, @Context UriInfo uriInfo) throws SQLException {
        Jogo jogo = modelMapper.map(jogoCreateDTO, Jogo.class);
        jogo.setDataCadastro(LocalDateTime.now()); // Definir a data de cadastro
        jogoDao.cadastrar(jogo);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(String.valueOf(jogo.getCodigo()));
        return Response.created(builder.build()).entity(modelMapper.map(jogo, JogoDetailDto.class)).build();
    }

    @GET
    public List<JogoDetailDto> listar() throws SQLException {
        List<Jogo> jogos = jogoDao.listar();
        return jogos.stream()
                .map(jogo -> modelMapper.map(jogo, JogoDetailDto.class))
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public JogoDetailDto buscarPorId(@PathParam("id") Integer id) throws SQLException, EntidadeNaoEncontradaException {
        Jogo jogo = jogoDao.buscarPorId(id);
        return modelMapper.map(jogo, JogoDetailDto.class);
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Integer id, JogoUpdateDto jogoUpdateDTO) throws SQLException, EntidadeNaoEncontradaException {
        Jogo jogoExistente = jogoDao.buscarPorId(id);
        modelMapper.map(jogoUpdateDTO, jogoExistente); // Atualiza o jogo existente com os dados do DTO
        jogoDao.atualizar(jogoExistente);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Integer id) throws SQLException, EntidadeNaoEncontradaException {
        jogoDao.deletar(id);
        return Response.noContent().build();
    }
}

