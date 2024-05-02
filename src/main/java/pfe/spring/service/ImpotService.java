package pfe.spring.service;

import pfe.spring.entity.Impot;

import java.util.List;

public interface ImpotService {


    public Impot saveImpot(Impot impot);

    public List<Impot> getAllImpots();

    public void deleteImpot(Long idImpot);

    public Impot updateImpot(Long idImpot);

    public Impot getImpot(Long idImpot);


}
