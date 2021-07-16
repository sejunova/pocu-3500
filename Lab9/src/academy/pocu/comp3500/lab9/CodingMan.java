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

        int answer = Integer.MAX_VALUE;
        int curStart = 0;
        int curEnd = 0;

        int startClipIdx = 0;
        boolean foundClips = false;

        for (int i = 0; i < videoClips.size(); i++) {
            VideoClip clip = videoClips.get(i);
            // 앞의 클립과 이어질 수 없는 경우
            if (clip.getStartTime() > curEnd) {
                curStart = clip.getStartTime();
                startClipIdx = i;
            }

            if (clip.getEndTime() <= curEnd) {
                continue;
            }

            curEnd = clip.getEndTime();

            int curTime = curEnd - curStart;
            if (curTime >= time) {
                if (startClipIdx == i) {
                    return 1;
                }
                foundClips = true;
                while (startClipIdx < i && curTime >= time) {
                    answer = Math.min(answer, i - startClipIdx + 1);
                    startClipIdx++;
                    curStart = videoClips.get(startClipIdx).getStartTime();
                    curTime = curEnd - curStart;
                }
            }
        }
        return (foundClips) ? answer : - 1;
    }

    public static void main(String[] args) {
        VideoClip[] clips = new VideoClip[]{
                new VideoClip(0, 20),
                new VideoClip(15, 30),
                new VideoClip(50, 90),
                new VideoClip(100, 101),

        };

        int count = CodingMan.findMinClipsCount(clips, 30);
        System.out.println(count);
        count = CodingMan.findMinClipsCount(clips, 31);
        System.out.println(count);

    }
}