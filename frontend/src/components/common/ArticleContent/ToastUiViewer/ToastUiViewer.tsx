import '@toast-ui/editor/dist/toastui-editor.css';
import { Viewer } from '@toast-ui/react-editor';

export interface ToastUiContentProps {
	initContent: string;
}

const ToastUiViewer = ({ initContent }: ToastUiContentProps) => (
	<Viewer initialValue={initContent} />
);

export default ToastUiViewer;
