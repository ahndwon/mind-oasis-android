package xyz.thingapps.mind_oasis.util

import xyz.thingapps.mind_oasis.model.Maxim
import java.util.*

class Dummies {
    companion object {
        private const val JOY = "joy"
        val maxim1 = Maxim(UUID.randomUUID().toString(), 0, "슬픔은 일순의 고통이다. 슬픔에 잠김은 인생의 가장 큰 실수이다.", "B. 디즈레일리", JOY)
        val maxim2 = Maxim(UUID.randomUUID().toString(), 1, "늦게 오는 기쁨은 늦게 떠난다.", "L. J. 베이츠", JOY)
        val maxim3 = Maxim(UUID.randomUUID().toString(), 2, "승리하는 자가 웃는다. ", "J. 헤이우드", JOY)
        val maxim4 =
                Maxim(UUID.randomUUID().toString(), 3, "꽃을 받는 것은 정말 멋진 일이다. 당신이 아직 꽃향기를 맡을 수 있는 동안에는. ", "리너 혼", JOY)
        val maxim5 = Maxim(UUID.randomUUID().toString(), 4, "고기를 낚으러 가는 노인의 가슴 속엔 언제나 어린 소년이 들어 있다.", "J.콜더 조셉", JOY)
        val maxim6 = Maxim(UUID.randomUUID().toString(), 5, "유머란 깊이 있는 관찰 결과를 다정하게 전달하는 방법.", "리오 로스튼", JOY)
        val maxim7 = Maxim(UUID.randomUUID().toString(), 6, "웃음소리는 울음소리보다 멀리 간다.", "히브리 격언", JOY)
        val maxim8 =
                Maxim(UUID.randomUUID().toString(), 7, "자기 자신에 대해 웃을 수 있는 사람처럼 행복한 사람은 없다. 매일 웃으면서 살 테니.", "하비브 부르기바", JOY)
        val maxim9 = Maxim(UUID.randomUUID().toString(), 8, "누구나 다 즐겁게 해주려면 결국 아무도 즐겁게 해줄 수 없다.", "이솝", JOY)
        val maxim10 = Maxim(UUID.randomUUID().toString(), 9, "시(詩)란 즐거움으로 시작해서 지혜로 끝나는 것.", "로버트 프로스트", JOY)

        val dummyMaximList = listOf(maxim1, maxim2, maxim3, maxim4, maxim5, maxim6,
                maxim7, maxim8, maxim9, maxim10)
    }
}
