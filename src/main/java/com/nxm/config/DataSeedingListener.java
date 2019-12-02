package com.nxm.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nxm.model.Pallet;
import com.nxm.model.PalletPosition;
import com.nxm.model.Role;
import com.nxm.model.User;
import com.nxm.repository.PalletPoisitionRepository;
import com.nxm.repository.PalletRepository;
import com.nxm.repository.RoleRepository;
import com.nxm.repository.UserRepository;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PalletRepository palletRepository;

	@Autowired
	private PalletPoisitionRepository palletPoisitionRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		/*
		 * // Roles thêm tài khoản begin 1 if (roleRepository.findByName("ROLE_ADMIN")
		 * == null) { roleRepository.save(new Role("ROLE_ADMIN")); }
		 * 
		 * if (roleRepository.findByName("ROLE_MEMBER") == null) {
		 * roleRepository.save(new Role("ROLE_MEMBER")); }
		 * 
		 * // Admin account if (userRepository.findByEmail("admin") == null) { User
		 * admin = new User(); admin.setEmail("leanhvu86@gmail.com");
		 * admin.setPassword(passwordEncoder.encode("123456")); HashSet<Role> roles =
		 * new HashSet<>(); roles.add(roleRepository.findByName("ROLE_ADMIN"));
		 * roles.add(roleRepository.findByName("ROLE_MEMBER")); admin.setRoles(roles);
		 * userRepository.save(admin); }
		 * 
		 * // Member account if
		 * (userRepository.findByEmail("nguyenxuanminh10@gmail.com") == null) { User
		 * user = new User(); user.setEmail("nguyenxuanminh10@gmail.com");
		 * user.setPassword(passwordEncoder.encode("123456")); HashSet<Role> roles = new
		 * HashSet<>(); roles.add(roleRepository.findByName("ROLE_MEMBER"));
		 * user.setRoles(roles); userRepository.save(user); } //end 1
		 */

	/*	//Lưu pallet_poisition begin 2
	  Integer pallet_id = 1;
		for (int i = 0; i < 45; i++) {
			Pallet palletTemp = palletRepository.findOne(pallet_id);
			if (palletTemp != null) {
				System.out.println(palletTemp);
				for (int hor = 0; hor < 4; hor++) {
					for (int ver = 0; ver < 3; ver++) {
						PalletPosition palletPositionTemp = new PalletPosition();
						palletPositionTemp.setPallet(palletTemp);
						if (hor == 0) {
							palletPositionTemp.setHorizontalAxis("A");
						} else if (hor == 1) {
							palletPositionTemp.setHorizontalAxis("B");
						} else if (hor == 2) {
							palletPositionTemp.setHorizontalAxis("C");
						} else if (hor == 3) {
							palletPositionTemp.setHorizontalAxis("D");
						}

						palletPositionTemp.setVerticalAxis(Integer.toString(ver+ 1) );
						palletPositionTemp.setEmptyPercent(0);
						palletPoisitionRepository.save(palletPositionTemp);
						System.out.println("Id pallet: "+palletPositionTemp.getPallet().getId()+"  hor: " + palletPositionTemp.getHorizontalAxis() + " ver: "+palletPositionTemp.getVerticalAxis()+" percent: "+palletPositionTemp.getEmptyPercent());
					}

				}

			}
			pallet_id++;
		}
		// end 2
		*/

	}

}