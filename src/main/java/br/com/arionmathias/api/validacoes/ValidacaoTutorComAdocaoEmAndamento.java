package br.com.arionmathias.api.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.arionmathias.api.dto.SolicitacaoAdocaoDto;
import br.com.arionmathias.api.exception.ValidacaoException;
import br.com.arionmathias.api.model.Adocao;
import br.com.arionmathias.api.model.StatusAdocao;
import br.com.arionmathias.api.model.Tutor;
import br.com.arionmathias.api.repository.AdocaoRepository;
import br.com.arionmathias.api.repository.TutorRepository;

import java.util.List;

@Component
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        List<Adocao> adocoes = adocaoRepository.findAll();
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        for (Adocao a : adocoes) {
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
            }
        }
    }

}
