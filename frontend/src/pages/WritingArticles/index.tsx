import { postWritingArticle } from '@/api/article';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import { AxiosResponse, AxiosError } from 'axios';
import { useEffect, useRef, useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';
import ToastUiEditor from './ToastUiEditor/ToastUiEditor';
import { Editor } from '@toast-ui/react-editor';
import * as S from '@/pages/WritingArticles/index.style';
import { CATEGORY } from '@/constants/categoryType';

const WritingArticles = () => {
	const { category } = useParams();

	const { data, mutate, isError, isLoading, isSuccess, error } = useMutation<
		AxiosResponse<{ id: string }>,
		AxiosError,
		{ title: string; category: string; content: string }
	>(postWritingArticle);

	const [title, setTitle] = useState('');
	const [categoryOption, setCategoryOption] = useState(category ? category : '');
	const content = useRef<Editor | null>(null);
	const navigate = useNavigate();

	useEffect(() => {
		if (isSuccess && category === CATEGORY.discussion) {
			if (confirm('글 등록이 완료되었습니다. 투표를 등록하시겠습니까?')) {
				navigate(`/votes/${data.data.id}`);
				return;
			}
			navigate(`/articles/${category}/${data.data.id}`);
		}
		if (isSuccess && category === CATEGORY.question) {
			navigate(`/articles/${category}/${data.data.id}`);
		}
	}, [isSuccess]);

	const handleSubmitButtonClick = () => {
		if (content.current === null) {
			return;
		}
		mutate({
			title,
			category: categoryOption,
			content: content.current.getInstance().getMarkdown(),
		});
	};

	if (isLoading) return <div>글 전송중 </div>;

	if (isError) {
		if (error instanceof Error) {
			return <div>{error.message}</div>;
		}
		return null;
	}

	return (
		<S.Container>
			<S.SelectorBox>
				<PageLayout width="100%" height="fit-content">
					<S.TitleInput
						type="text"
						placeholder="제목을 입력해주세요"
						value={title}
						onChange={(e) => setTitle(e.target.value)}
					/>
				</PageLayout>
				<PageLayout width="100%" height="fit-content">
					<S.CategorySelectorBox>
						<S.CategorySelector
							name="writing"
							required
							value={categoryOption}
							onChange={(e) => setCategoryOption(e.target.value)}
						>
							<option value="" disabled>
								카테고리를 선택해주세요
							</option>
							<option value="question">에러</option>
							<option value="discussion">토론</option>
						</S.CategorySelector>
						<S.SelectorButton />
					</S.CategorySelectorBox>
				</PageLayout>
				<PageLayout width="100%" height="fit-content">
					<S.HashTagInput type="text" placeholder="해쉬태그를 입력해주세요" />
				</PageLayout>
			</S.SelectorBox>

			<S.Content>
				<ToastUiEditor initContent={''} ref={content} />
			</S.Content>
			<S.SubmitButton type="button" onClick={handleSubmitButtonClick}>
				등록하기
			</S.SubmitButton>
		</S.Container>
	);
};

export default WritingArticles;
