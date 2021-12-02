package ma.octo.assignement.service.Impl;

import ma.octo.assignement.domain.AuditVersement;
import ma.octo.assignement.domain.AuditVirement;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditVersementRepository;
import ma.octo.assignement.repository.AuditVirementRepository;
import ma.octo.assignement.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AuditServiceImpli implements AuditService {
    Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpli.class);

    @Autowired
    AuditVirementRepository auditVirementRepository;

    @Autowired
    AuditVersementRepository auditVersementRepository;

    @Override
    public void auditVirement(String message) {
        LOGGER.info("Audit de l'événement {}", EventType.VIREMENT);
        auditVirementRepository.save(new AuditVirement(null, message, EventType.VIREMENT));
    }

    @Override
    public void auditVersement(String message) {
        LOGGER.info("Audit de l'événement {}", EventType.VERSEMENT);
        auditVersementRepository.save(new AuditVersement(null, message, EventType.VERSEMENT));
    }
}
