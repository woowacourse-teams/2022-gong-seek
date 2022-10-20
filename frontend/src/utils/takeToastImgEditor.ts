import { MutableRefObject } from 'react';

import { postImageUrlConverter } from '@/api/image';
import { Editor } from '@toast-ui/react-editor';

export const takeToastImgEditor = (content: MutableRefObject<Editor | null>) => {
	if (content.current) {
		content.current.getInstance().removeHook('addImageBlobHook');
		content.current.getInstance().addHook('addImageBlobHook', (blob, callback) => {
			(async () => {
				const formData = new FormData();
				formData.append('file', blob);
				const { url } = await postImageUrlConverter(formData);
				callback(url, 'alt-text');
			})();
		});
	}
};
