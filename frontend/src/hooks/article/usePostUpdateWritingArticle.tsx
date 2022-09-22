import { AxiosError, AxiosResponse } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import { putArticle } from '@/api/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { queryClient } from '@/index';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { articleState } from '@/store/articleState';
import { validatedTitleInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

const usePostUpdateWritingArticle = () => {
	const navigate = useNavigate();

	const tempArticle = useRecoilValue(articleState);

	const content = useRef<Editor | null>(null);
	const [title, setTitle] = useState<string>(tempArticle.title);
	const [hashTag, setHashTag] = useState<string[]>(tempArticle.tag);

	const [isValidTitleInput, setIsValidTitleInput] = useState(true);
	const titleInputRef = useRef<HTMLInputElement>(null);

	const { data, isSuccess, isError, isLoading, error, mutate } = useMutation<
		AxiosResponse<{ id: number; category: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ title: string; content: string; id: string; tag: string[] }
	>(putArticle, { retry: 1 });

	useEffect(() => {
		if (isSuccess) {
			navigate(`/articles/${data.data.category}/${data.data.id}`);
		}
	}, [isSuccess]);

	useThrowCustomError(isError, error);

	const handleUpdateButtonClick = (id: string) => {
		if (content.current === null) {
			return;
		}
		if (!validatedTitleInput(title)) {
			setIsValidTitleInput(false);

			if (titleInputRef.current !== null) {
				titleInputRef.current.focus();
			}
			return;
		}
		setIsValidTitleInput(true);
		mutate(
			{ title, content: content.current.getInstance().getMarkdown(), id, tag: hashTag },
			{
				onSuccess: ({ data }) => {
					queryClient.refetchQueries(['detail-article', `article${data.id}`]);
					window.location.href = `/articles/${data.category}/${data.id}`;
				},
			},
		);
	};

	return {
		isLoading,
		title,
		setTitle,
		tempArticle,
		content,
		hashTag,
		setHashTag,
		titleInputRef,
		isValidTitleInput,
		handleUpdateButtonClick,
	};
};

export default usePostUpdateWritingArticle;
