package org.infosystema.peakcoin.data;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.infosystema.peakcoin.enums.ClaimStatus;

/**
 * 
 * @author Kuttubek Aidaraliev
 *
 */

@Named
@RequestScoped
public class ClaimStatusData {
	
	public List<ClaimStatus> getAll() {
		return Arrays.asList(ClaimStatus.values());
	}
	
}
