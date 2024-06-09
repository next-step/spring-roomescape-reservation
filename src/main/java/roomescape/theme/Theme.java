package roomescape.theme;

public class Theme {
	private Long id;

	private String name;

	private String description;

	private String thumbnail;

	public Theme(Long id, String name, String description, String thumbnail) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.thumbnail = thumbnail;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getThumbnail() {
		return thumbnail;
	}
}
