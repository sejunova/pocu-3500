package academy.pocu.comp3500.lab9;

import academy.pocu.comp3500.lab9.data.VideoClip;

import java.util.Arrays;
import java.util.Comparator;

public class CodingMan {
    public static int findMinClipsCount(final VideoClip[] clips, int time) {
        Arrays.sort(clips, Comparator.comparingInt(VideoClip::getStartTime));

        int answer = Integer.MAX_VALUE;
        int curStart = 0;
        int curEnd = 0;

        int startClipIdx = 0;

        for (int i = 0; i < clips.length; i++) {
            VideoClip clip = clips[i];
            // 앞의 클립과 이어질 수 없는 경우
            if (clip.getStartTime() > curEnd) {
                curStart = clip.getStartTime();
                startClipIdx = i;
            }

            curEnd = clip.getEndTime();

            int curTime = curEnd - curStart;
            if (curTime >= time) {
                if (startClipIdx == i) {
                    return 1;
                }
                while (startClipIdx < i && curTime >= time) {
                    answer = Math.min(answer, i - startClipIdx + 1);
                    startClipIdx++;
                    curStart = clips[startClipIdx].getStartTime();
                    curTime = curEnd - curStart;
                }
            }
        }
        return (answer == Integer.MAX_VALUE) ? -1 : answer;
    }

    public static void main(String[] args) {
        VideoClip[] clips = new VideoClip[]{
                new VideoClip(0, 15),
                new VideoClip(10, 20),
                new VideoClip(30, 35)
        };

        int count = CodingMan.findMinClipsCount(clips, 10); // 1
        System.out.println(count);
        count = CodingMan.findMinClipsCount(clips, 20); // 2
        System.out.println(count);
        count = CodingMan.findMinClipsCount(clips, 25); // -1
        System.out.println(count);
        count = CodingMan.findMinClipsCount(clips, 35); // -1
        System.out.println(count);

    }
}