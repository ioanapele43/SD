package ro.tuc.ds2020.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import java.util.Objects;
import java.util.UUID;
@Getter
@Setter
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id;
    private String description;
    private String address;
    private int consumption;

    public DeviceDTO(){

    }
    public DeviceDTO(UUID id, String description, String address, int consumption){
        this.id=id;
        this.description=description;
        this.address=address;
        this.consumption=consumption;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o==null || getClass()!=o.getClass()) return false;
        DeviceDTO deviceDTO=(DeviceDTO) o;
        return (Objects.equals(description, deviceDTO.description) && Objects.equals(address, deviceDTO.description) && Objects.equals(consumption, deviceDTO.consumption));
    }
    @Override
    public int hashCode(){return Objects.hash(id, description);}
}
