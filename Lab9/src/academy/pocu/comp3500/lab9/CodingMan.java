package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.Arrays;

public class CodingMan {
    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        if (clips.length == 0) {
            return -1;
        }

        Arrays.sort(clips, (v0, v1) -> {
            if (v0.getStartTime() > v1.getStartTime()) {
                return 1;
            } else if (v0.getStartTime() < v1.getStartTime()) {
                return -1;
            }
            return Integer.compare(v1.getEndTime(), v0.getEndTime());
        });

        VideoClip prev = clips[0];
        if (prev.getStartTime() != 0) {
            return -1;
        }
        if (prev.getEndTime() >= time) {
            return 1;
        }

        int answer = 1;
        for (int i = 1; i < clips.length; i++) {
            VideoClip clip = clips[i];
            // 1. 이전 클립과 연결이 안 되는 경우
            if (clip.getStartTime() > prev.getEndTime()) {
                return -1;
            }

            // 2. 이전 클립에 포함되는 경우
            if (clip.getEndTime() <= prev.getEndTime()) {
                continue;
            }

            // 3. 이전 클립을 완전히 포함하는 경우
            if (clip.getStartTime() <= prev.getStartTime()) {
                prev = new VideoClip(prev.getStartTime(), clip.getEndTime());
                continue;
            }

            // 이전 클립을 세분화시켜서 보관
            answer++;
            if (clip.getEndTime() >= time) {
                return answer;
            }
            prev = new VideoClip(prev.getEndTime(), clip.getEndTime());
        }
        return -1;
    }
}