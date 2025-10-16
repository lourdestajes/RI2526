package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.commands.ListAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicById;
import uo.ri.cws.application.service.mechanic.crud.commands.ListMechanicByNif;
import uo.ri.cws.application.service.mechanic.crud.commands.UpdateMechanic;
import uo.ri.util.exception.BusinessException;

public class MechanicCrudServiceImpl implements MechanicCrudService {

     
    public MechanicDto create(MechanicDto dto) throws BusinessException {
        return new AddMechanic(dto).execute();
    }

     
    public void delete(String mechanicId) throws BusinessException {
        new DeleteMechanic(mechanicId).execute();
    }

     
    public void update(MechanicDto dto) throws BusinessException {
        new UpdateMechanic(dto).execute();
    }

     
    public Optional<MechanicDto> findById(String id) throws BusinessException {
        return new ListMechanicById(id).execute();
    }

     
    public Optional<MechanicDto> findByNif(String nif)
            throws BusinessException {
        return new ListMechanicByNif(nif).execute();
    }

     
    public List<MechanicDto> findAll() throws BusinessException {
        return new ListAllMechanics().execute();
    }

}
