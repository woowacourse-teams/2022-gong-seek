import { MutableRefObject, useEffect } from 'react';

import { postImageUrlConverter } from '@/api/image';
import { Editor } from '@toast-ui/react-editor';

const useConvertImageUrl = (content: MutableRefObject<Editor | null>) => {
	useEffect(() => {
		if (content.current) {
			content.current.getInstance().removeHook('addImageBlobHook');
			content.current.getInstance().addHook('addImageBlobHook', (blob, callback) => {
				(async () => {
					const formData = new FormData();

					formData.append('imageFile', blob);
					const url = await postImageUrlConverter(formData);
					callback(url, 'alt-text');
				})();
			});
		}
	}, [content]);
};

export default useConvertImageUrl;
