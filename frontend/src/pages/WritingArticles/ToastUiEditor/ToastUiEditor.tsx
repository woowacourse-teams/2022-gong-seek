import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { forwardRef, LegacyRef } from 'react';

const ToastUiEditor = forwardRef((props: { initContent: string }, ref: LegacyRef<Editor>) => (
	<Editor
		initialValue={props.initContent}
		previewStyle="tab"
		height="30rem"
		initialEditType="markdown"
		useCommandShortcut={true}
		ref={ref}
	/>
));

ToastUiEditor.displayName = 'ToastUiEditor';

export default ToastUiEditor;
