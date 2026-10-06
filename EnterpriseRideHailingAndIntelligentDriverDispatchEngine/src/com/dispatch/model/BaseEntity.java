package com.dispatch.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseEntity implements Serializable {
	 private static final long serialVersionUID = 1L;
	    private static final Logger LOGGER = Logger.getLogger(BaseEntity.class.getName());

	    // Explicit static variable demonstration
	    private static String systemClusterId;

	    protected final String id;
	    protected final String name;
	    protected final Instant createdAt;
	    protected Instant updatedAt;

	    // Static Initialization Block (Class-level bootstrapping)
	    static {
	        try {
	            systemClusterId = System.getenv().getOrDefault("CLUSTER_ID", "BLR-REGION-01");
	            LOGGER.info("BaseEntity initialized with System Cluster ID: " + systemClusterId);
	        } catch (Exception e) {
	            systemClusterId = "BLR-REGION-DEFAULT";
	            LOGGER.log(Level.WARNING, "Failed to read environment cluster ID, fallback used.", e);
	        }
	    }

	    // Instance Initialization Block (Executes prior to constructors)
	    {
	        this.createdAt = Instant.now();
	        this.updatedAt = this.createdAt;
	    }

	    // Overloaded Constructors
	    public BaseEntity(String id) {
	        this(id, "UNASSIGNED");
	    }

	    public BaseEntity(String id, String name) {
	        this.id = Objects.requireNonNull(id, "Entity 'id' must not be null");
	        this.name = Objects.requireNonNullElse(name, "UNASSIGNED");
	    }

	    // Core Getters
	    public String getId() { 
	        return id; 
	    }

	    public String getName() { 
	        return name; 
	    }

	    public Instant getCreatedAt() { 
	        return createdAt; 
	    }

	    public Instant getUpdatedAt() { 
	        return updatedAt; 
	    }

	    public static String getSystemClusterId() { 
	        return systemClusterId; 
	    }

	    public static void setSystemClusterId(String clusterId) {
	        systemClusterId = Objects.requireNonNull(clusterId, "clusterId cannot be null");
	    }

	    // State Mutation Utility
	    public void markUpdated() {
	        this.updatedAt = Instant.now();
	    }

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;
	        BaseEntity that = (BaseEntity) o;
	        return Objects.equals(id, that.id);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }

	    @Override
	    public String toString() {
	        return getClass().getSimpleName() + "{" +
	                "id='" + id + '\'' +
	                ", name='" + name + '\'' +
	                ", cluster='" + systemClusterId + '\'' +
	                ", createdAt=" + createdAt +
	                ", updatedAt=" + updatedAt +
	                '}';
	    }
	}