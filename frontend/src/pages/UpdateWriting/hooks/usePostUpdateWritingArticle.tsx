import { putArticle } from '@/api/article';
import { articleState } from '@/store/articleState';
import { Editor } from '@toast-ui/react-editor';
import { AxiosError, AxiosResponse } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

const usePostUpdateWritingArticle = () => {
	const navigate = useNavigate();

	const tempArticle = useRecoilValue(articleState);

	const content = useRef<Editor | null>(null);
	const [title, setTitle] = useState(tempArticle.title);

	const { data, isError, isSuccess, isLoading, error, mutate } = useMutation<
		AxiosResponse<{ id: number; category: string }>,
		AxiosError,
		{ title: string; content: string; id: string }
	>(putArticle);

	useEffect(() => {
		if (isSuccess) {
			navigate(`/articles/${data.data.category}/${data.data.id}`);
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	const handleUpdateButtonClick = (id: string) => {
		if (content.current === null) {
			return;
		}
		mutate({ title, content: content.current.getInstance().getMarkdown(), id });
	};

	return { isLoading, title, setTitle, tempArticle, content, handleUpdateButtonClick };
};

export default usePostUpdateWritingArticle;
