package kr.nutee.nuteebackend.Service;

import kr.nutee.nuteebackend.Domain.Image;
import kr.nutee.nuteebackend.Domain.Member;
import kr.nutee.nuteebackend.Repository.ImageRepository;
import kr.nutee.nuteebackend.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
@RequiredArgsConstructor
@ResponseBody
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Image uploadProfile(Long memberId, String src){
        Member member = memberRepository.findMemberById(memberId);
        //Todo:해당 멤버 없을 시 예외
        Image image = Image.builder().member(member).src(src).build();
        return imageRepository.save(image);
    }

    @Transactional
    public void deleteProfile(Long memberId){
        imageRepository.deleteImagesByMemberId(memberId);
    }
}
