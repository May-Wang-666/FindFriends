package cn.edu.zju.socialnetwork.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseMoments {

    private List<MomentWithLike> moments;
    private boolean isLastPage;

    public ResponseMoments(){}

    public ResponseMoments(List<MomentWithLike> moments, boolean isLastPage) {
        this.moments = moments;
        this.isLastPage = isLastPage;
    }
}
