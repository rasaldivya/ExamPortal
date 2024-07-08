package com.exam.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Learnings {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private long tId;
	    private  String tName;
	    private String description;
		private String topic;
	    private  String trade;
	    private String fileName;
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;
		public Learnings() {
			super();
		}

	public Learnings(long tId, String tName, String description, String topic, String trade, String fileName) {
		this.tId = tId;
		this.tName = tName;
		this.description = description;
		this.topic = topic;
		this.trade = trade;
		this.fileName = fileName;
	}

	public long gettId() {
			return tId;
		}
		public void settId(long tId) {
			this.tId = tId;
		}
		
		public String gettName() {
			return tName;
		}
		public void settName(String tName) {
			this.tName = tName;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getTrade() {
			return trade;
		}
		public void setTrade(String trade) {
			this.trade = trade;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Learnings{" +
				"tId=" + tId +
				", tName='" + tName + '\'' +
				", description='" + description + '\'' +
				", topic='" + topic + '\'' +
				", trade='" + trade + '\'' +
				", fileName='" + fileName + '\'' +
				'}';
	}
}
