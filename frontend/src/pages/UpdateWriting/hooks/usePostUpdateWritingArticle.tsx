import { AxiosError, AxiosResponse } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { putArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { articleState } from '@/store/articleState';
import { Editor } from '@toast-ui/react-editor';

const usePostUpdateWritingArticle = () => {
	const navigate = useNavigate();

	const tempArticle = useRecoilValue(articleState);

	const content = useRef<Editor | null>(null);
	const [title, setTitle] = useState<string>(tempArticle.title);
	const [hashTag, setHashTag] = useState<string[]>(tempArticle.hashTag);

	const { data, isError, isSuccess, isLoading, error, mutate } = useMutation<
		AxiosResponse<{ id: number; category: string }>,
		AxiosError<{ errorCode: string; message: string }>,
		{ title: string; content: string; id: string; hashTag: string[] }
	>(putArticle);

	useEffect(() => {
		if (isSuccess) {
			navigate(`/articles/${data.data.category}/${data.data.id}`);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, [isError]);

	const handleUpdateButtonClick = (id: string) => {
		if (content.current === null) {
			return;
		}
		mutate({ title, content: content.current.getInstance().getMarkdown(), id, hashTag });
	};

	return {
		isLoading,
		title,
		setTitle,
		tempArticle,
		content,
		hashTag,
		setHashTag,
		handleUpdateButtonClick,
	};
};

export default usePostUpdateWritingArticle;
