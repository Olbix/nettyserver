package pl.wlodarski.nio;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Builder
@ToString
public class EyetrackerMsg implements Serializable {
    private static final long serialVersionUID = 950824;
    String id;
    double x;
    double y;
}