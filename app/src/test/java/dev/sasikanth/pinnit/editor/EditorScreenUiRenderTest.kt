package dev.sasikanth.pinnit.editor

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import dev.sasikanth.pinnit.TestData
import org.junit.Test
import java.util.UUID

class EditorScreenUiRenderTest {

  private val ui = mock<EditorScreenUi>()
  private val uiRender = EditorScreenUiRender(ui)

  private val notificationUuid = UUID.fromString("62f36ab9-9a54-481a-9db7-c856766975ce")

  @Test
  fun `when no notification is present, then update ui`() {
    // given
    val model = EditorScreenModel.default(null, null, null)
      .titleChanged(null)

    // when
    uiRender.render(model)

    // then
    verify(ui).renderSaveAndPinActionButtonText()
    verify(ui).hideDeleteButton()
    verify(ui).disableSave()
    verifyNoMoreInteractions(ui)
  }

  @Test
  fun `when notification is present, then update ui`() {
    // given
    val notification = TestData.notification(
      uuid = notificationUuid
    )
    val model = EditorScreenModel.default(notificationUuid, null, null)
      .notificationLoaded(notification)
      .titleChanged(notification.title)
      .contentChanged(notification.content)

    // when
    uiRender.render(model)

    // then
    verify(ui).renderSaveActionButtonText()
    verify(ui).showDeleteButton()
    verify(ui).disableSave()
    verifyNoMoreInteractions(ui)
  }

  @Test
  fun `when notification is present and title is changed, then enable save`() {
    val notificationTitle = "Notification Title"

    val model = EditorScreenModel.default(notificationUuid, null, null)
      .notificationLoaded(
        TestData.notification(
          uuid = notificationUuid,
          title = "Original Notification Title"
        )
      )
      .titleChanged(notificationTitle)

    // then
    uiRender.render(model)

    // then
    verify(ui).enableSave()
    verify(ui).showDeleteButton()
    verify(ui).renderSaveActionButtonText()
    verifyNoMoreInteractions(ui)
  }

  @Test
  fun `when title is blank, then disable save`() {
    val title = ""

    val model = EditorScreenModel.default(notificationUuid, null, null)
      .titleChanged(title)

    // then
    uiRender.render(model)

    // then
    verify(ui).disableSave()
    verify(ui).hideDeleteButton()
    verify(ui).renderSaveAndPinActionButtonText()
    verifyNoMoreInteractions(ui)
  }
}
