import { AxiosError, AxiosResponse } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { postWritingArticle } from '@/api/article/article';
import {
	CategoryType,
	CreateArticleRequestType,
	CreateArticleResponseType,
} from '@/api/article/articleType';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { CATEGORY } from '@/constants/categoryType';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { validatedTitleInput } from '@/utils/validateInput';
import { Editor } from '@toast-ui/react-editor';

const usePostWritingArticles = ({
	category,
	isAnonymous,
}: {
	category?: string;
	isAnonymous: boolean;
}) => {
	const { data, mutate, isError, isLoading, isSuccess, error } = useMutation<
		AxiosResponse<CreateArticleResponseType>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		CreateArticleRequestType
	>(postWritingArticle, { retry: 1 });

	const content = useRef<Editor | null>(null);
	const [title, setTitle] = useState('');
	const [categoryOption, setCategoryOption] = useState<string>(category ? category : '');
	const [isValidTitleInput, setIsValidTitleInput] = useState(true);
	const [hashTags, setHashTags] = useState<string[]>([]);
	const titleInputRef = useRef<HTMLInputElement>(null);
	const navigate = useNavigate();

	useThrowCustomError(isError, error);

	if (typeof category === 'undefined') {
		throw new Error('카테고리가 존재하지 않습니다.');
	}

	useEffect(() => {
		if (isSuccess && categoryOption === CATEGORY.discussion) {
			if (confirm('글 등록이 완료되었습니다. 투표를 등록하시겠습니까?')) {
				navigate(`/votes/${data.data.id}`);
				return;
			}
			navigate(`/articles/${categoryOption}/${data.data.id}`);
		}
		if (isSuccess && categoryOption === CATEGORY.question) {
			navigate(`/articles/${categoryOption}/${data.data.id}`);
		}
	}, [isSuccess]);

	const handleSubmitButtonClick = (categoryOption: CategoryType, tempArticleId: number | '') => {
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
		mutate({
			title: title,
			category: categoryOption,
			content: content.current.getInstance().getMarkdown(),
			tag: hashTags,
			isAnonymous,
			tempArticleId,
		});
	};

	return {
		handleSubmitButtonClick,
		content,
		isLoading,
		title,
		titleInputRef,
		isValidTitleInput,
		setTitle,
		hashTags,
		setHashTags,
		categoryOption,
		setCategoryOption,
	};
};

export default usePostWritingArticles;
