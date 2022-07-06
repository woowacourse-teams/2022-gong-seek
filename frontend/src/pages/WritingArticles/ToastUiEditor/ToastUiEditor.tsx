import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { forwardRef, LegacyRef } from 'react';

const ToastUiEditor = forwardRef((_, ref: LegacyRef<Editor>) => (
	<Editor
		initialValue=""
		previewStyle="tab"
		height="600px"
		initialEditType="markdown"
		useCommandShortcut={true}
		ref={ref}
	/>
));

ToastUiEditor.displayName = 'ToastUiEditor';

export default ToastUiEditor;
