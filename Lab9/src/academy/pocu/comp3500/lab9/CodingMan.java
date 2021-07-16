package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.ArrayList;
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
            return Integer.compare(v0.getEndTime(), v1.getEndTime());
        });

        if (clips[0].getStartTime() != 0) {
            return -1;
        }

        ArrayList<VideoClip> videoClips = new ArrayList<>(clips.length);
        videoClips.add(clips[0]);
        for (int i = 1; i < clips.length; i++) {
            int lastIdx = videoClips.size() - 1;
            VideoClip lastElement = videoClips.get(lastIdx);
            if (lastElement.getStartTime() == clips[i].getStartTime()) {
                videoClips.set(lastIdx, clips[i]);
            } else if (clips[i].getEndTime() > lastElement.getEndTime()) {
                videoClips.add(clips[i]);
            }
        }

        if (videoClips.get(videoClips.size() - 1).getEndTime() - videoClips.get(0).getStartTime() < time) {
            return -1;
        }

        int curStart = 0;
        int curEnd = 0;

        for (int i = 0; i < videoClips.size(); i++) {
            VideoClip clip = videoClips.get(i);
            // 앞의 클립과 이어질 수 없는 경우
            if (clip.getStartTime() > curEnd) {
                return -1;
            }

            curEnd = clip.getEndTime();
            int curTime = curEnd - curStart;
            if (curTime >= time) {
                return i + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        VideoClip[] clips = new VideoClip[]{
                new VideoClip(0, 10),
        };
        int airTime = 10;

        int count = CodingMan.findMinClipsCount(clips, airTime);

        System.out.println(1); // 1

        clips = new VideoClip[]{
                new VideoClip(30, 60),
                new VideoClip(0, 20)
        };
        airTime = 60;

        count = CodingMan.findMinClipsCount(clips, airTime);

        System.out.println(-1); // -1

        clips = new VideoClip[]{
                new VideoClip(0, 5),
                new VideoClip(0, 20),
                new VideoClip(5, 30),
                new VideoClip(25, 35),
                new VideoClip(35, 70),
                new VideoClip(50, 75)
        };
        airTime = 60;

        count = CodingMan.findMinClipsCount(clips, airTime);

        System.out.println(4); // 4

    }
}