package capstone.product.tim.majorsmatch.testing

import capstone.product.tim.majorsmatch.response.ListStoryItem
import capstone.product.tim.majorsmatch.response.StoryResponse

object DataDummy {

    fun generateDummyQuoteResponse(): StoryResponse {
        val listStory = ArrayList<ListStoryItem>()
        for (i in 1..20) {
            val listStoryItem = ListStoryItem(
                createdAt = "2023-09-05T05:05:05Z",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                name = "Name $i",
                photoUrl = "https://ichef.bbci.co.uk/onesport/cps/976/cpsprodpb/AA43/production/_130978534_gettyimages-1644160385.jpg"
            )
            listStory.add(listStoryItem)
        }
        return StoryResponse( error = false, message = "Stories fetched successfully", listStory = listStory)
    }
}