package academy.pocu.comp3500.lab3;

import java.util.ArrayList;
import java.util.Comparator;

public final class MissionControl {
    private MissionControl() {
    }

    public static int findMaxAltitudeTime(final int[] altitudes) {
        if (altitudes.length == 1 || altitudes[0] > altitudes[1]) {
            return 0;
        } else if (altitudes[altitudes.length - 2] < altitudes[altitudes.length - 1]) {
            return altitudes.length - 1;
        } else {
            return findMaxIndex(altitudes);
        }
    }

    public static ArrayList<Integer> findAltitudeTimes(final int[] altitudes, final int targetAltitude) {
        ArrayList<Integer> answer = new ArrayList<>();
        if (altitudes.length == 1) {
            if (altitudes[0] == targetAltitude) {
                answer.add(0);
            }
            return answer;
        }

        if (altitudes[0] > altitudes[1]) {
            binarySearch(altitudes, 0, altitudes.length - 1, targetAltitude, Comparator.reverseOrder(), answer);
        } else if (altitudes[altitudes.length - 2] < altitudes[altitudes.length - 1]) {
            binarySearch(altitudes, 0, altitudes.length - 1, targetAltitude, Integer::compareTo, answer);
        } else {
            int maxIndex = findMaxIndex(altitudes);
            if (altitudes[maxIndex] == targetAltitude) {
                answer.add(maxIndex);
            } else {
                binarySearch(altitudes, 0, maxIndex - 1, targetAltitude, Integer::compareTo, answer);
                binarySearch(altitudes, maxIndex + 1, altitudes.length - 1, targetAltitude, Comparator.reverseOrder(), answer);
            }
        }
        return answer;
    }

    private static int findMaxIndex(final int[] altitudes) {
        int left = 0;
        int right = altitudes.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (altitudes[mid] < altitudes[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static void binarySearch(final int[] altitudes, int left, int right, int targetAltitude, Comparator<Integer> comparator, ArrayList<Integer> times) {
        int mid = left + (right - left) / 2;
        while (left <= right) {
            if (altitudes[mid] == targetAltitude) {
                times.add(mid);
                return;
            }
            if (comparator.compare(altitudes[mid], targetAltitude) > 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
            mid = left + (right - left) / 2;
        }
    }
}