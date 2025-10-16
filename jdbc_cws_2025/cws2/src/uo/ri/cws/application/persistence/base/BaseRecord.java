package uo.ri.cws.application.persistence.base;

import java.time.LocalDateTime;
import java.util.UUID;

public class BaseRecord {
	
	public String id = UUID.randomUUID().toString();
    public String entityState = "ENABLED"; // ENABLED, DISABLED
    public long version = 1;
    public LocalDateTime createdAt = LocalDateTime.now();
    public LocalDateTime updatedAt = createdAt;
    
}
