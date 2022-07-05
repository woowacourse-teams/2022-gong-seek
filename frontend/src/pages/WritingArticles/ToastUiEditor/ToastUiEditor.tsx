import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';
import { useRef } from 'react';

const ToastUiEditor = () => {
	const editorRef = useRef(null);

	return (
		<Editor
			initialValue=""
			previewStyle="tab"
			height="600px"
			initialEditType="markdown"
			useCommandShortcut={true}
			ref={editorRef}
		/>
	);
};

export default ToastUiEditor;
