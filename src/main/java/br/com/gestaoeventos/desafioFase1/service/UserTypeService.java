package br.com.gestaoeventos.desafioFase1.service;

import br.com.gestaoeventos.desafioFase1.domain.UserType;
import br.com.gestaoeventos.desafioFase1.repository.UserRepository;
import br.com.gestaoeventos.desafioFase1.repository.UserTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserTypeService {

	private final UserTypeRepository userTypeRepository;
	private final UserRepository userRepository;

	public UserTypeService(UserTypeRepository userTypeRepository, UserRepository userRepository) {
		this.userTypeRepository = userTypeRepository;
		this.userRepository = userRepository;
	}

	@Transactional
	public UserType create(UserType userType) {
		validateUniqueName(userType.getNomeTipo(), null);
		return saveWithIntegrityGuard(userType);
	}

	public List<UserType> listAll() {
		return userTypeRepository.findAll();
	}

	public UserType getById(Long id) {
		return userTypeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Tipo de usuário não encontrado"));
	}

	@Transactional
	public UserType update(Long id, UserType userType) {
		UserType existing = getById(id);
		validateUniqueName(userType.getNomeTipo(), id);
		existing.setNomeTipo(userType.getNomeTipo());
		return saveWithIntegrityGuard(existing);
	}

	@Transactional
	public void delete(Long id) {
		UserType existing = getById(id);
		long linkedUsers = userRepository.countByTipoId(id);
		if (linkedUsers > 0) {
			throw new IllegalArgumentException("Não é possível excluir o tipo. Há usuários associados a ele");
		}
		userTypeRepository.delete(existing);
	}

	private void validateUniqueName(String nomeTipo, Long idToIgnore) {
		userTypeRepository.findByNomeTipoIgnoreCase(nomeTipo)
				.filter(found -> !found.getId().equals(idToIgnore))
				.ifPresent(found -> {
					throw new IllegalArgumentException("Já existe um tipo de usuário com este nome");
				});
	}

	private UserType saveWithIntegrityGuard(UserType userType) {
		try {
			return userTypeRepository.save(userType);
		} catch (DataIntegrityViolationException ex) {
			throw new IllegalArgumentException("Já existe um tipo de usuário com este nome");
		}
	}
}

