import { AxiosError, AxiosResponse } from 'axios';
import { MutableRefObject, useEffect, useState } from 'react';
import { useMutation } from 'react-query';

import { postImageUrlConverter } from '@/api/image';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { Editor } from '@toast-ui/react-editor';

const useToastImageConverter = (content: MutableRefObject<Editor | null>) => {
	const { showSnackBar } = useSnackBar();
	const [blockThrowError, setBlockThrowError] = useState(false);
	const { isError, error, mutateAsync } = useMutation<
		AxiosResponse<{ url: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		FormData
	>(postImageUrlConverter, {
		onError: (error) => {
			setBlockThrowError(true);
			if (typeof error.response?.data === 'undefined') {
				showSnackBar('컨텐츠의 용량이 너무 큽니다.');
				return;
			}
			if (error.response.data.errorCode === '7002') {
				showSnackBar(ErrorMessage[error.response.data.errorCode]);
				return;
			}
		},
	});

	useEffect(() => {
		if (content.current) {
			content.current.getInstance().removeHook('addImageBlobHook');
			content.current.getInstance().addHook('addImageBlobHook', (blob, callback) => {
				(async () => {
					const formData = new FormData();
					formData.append('file', blob);
					const data = await mutateAsync(formData);
					callback(data.data.url, '게시물 이미지');
				})();
			});
		}
	}, [content.current]);

	useThrowCustomError(isError, error, blockThrowError, setBlockThrowError);
};

export default useToastImageConverter;
