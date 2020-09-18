package org.infosystema.peakcoin.domain;

import java.io.Serializable;

/**
 * @param <PK>
 * @author Kuttubek Aidaraliev
 */

public interface PersistentEntity<PK extends Serializable> extends Serializable {

    PK getId();

    void setId(PK pk);

}