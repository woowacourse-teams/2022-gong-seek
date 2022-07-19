import { postArticle, putArticle } from '@/api/article';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import * as S from '@/pages/WritingArticles/index.style';
import { useEffect, useRef } from 'react';
import { Editor } from '@toast-ui/react-editor';
import { AxiosError, AxiosResponse } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';
import ToastUiEditor from '../WritingArticles/ToastUiEditor/ToastUiEditor';

// interface UpdateWritingProps {
// 	initContent: string;
// 	initTitle: string;
// }

const UpdateWriting = () => {
	const { id } = useParams();
	const { category } = useParams();
	const navigate = useNavigate();
	let initTitle = localStorage.getItem('title');
	let initContent = localStorage.getItem('content');
	const { data, isError, isSuccess, isLoading, error, mutate } = useMutation<
		AxiosResponse<{ id: number; category: string }>,
		AxiosError,
		{ title: string; content: string; id: string }
	>(putArticle);

	const content = useRef<Editor | null>(null);
	const [title, setTitle] = useState('test');
	if (initTitle === undefined || initTitle === '' || initTitle === null) {
		initTitle = 'test';
	}
	if (initContent === undefined || initContent === '' || initContent === null) {
		initContent = 'test';
	}

	if (id === undefined || category === undefined) {
		throw new Error('id와  category 값을 가지고 오지 못하였습니다');
	}

	const handleUpdateButtonClick = () => {
		if (content.current === null) {
			return;
		}
		mutate({ title, content: content.current.getInstance().getMarkdown(), id });
	};

	useEffect(() => {
		if (isSuccess) {
			console.log('수정에 성공하였습니다');
			navigate(`/articles/${category}/${id}`);
		}
	}, [isSuccess]);

	return (
		<S.Container>
			<S.SelectorBox>
				<PageLayout width="100%" height="fit-content">
					<S.TitleInput
						type="text"
						placeholder="제목을 입력해주세요"
						value={initTitle}
						onChange={(e) => setTitle(e.target.value)}
					/>
				</PageLayout>
				<PageLayout width="100%" height="fit-content">
					<S.CategorySelectorBox>
						<S.CategorySelector name="writing" required value={category} disabled>
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
				<ToastUiEditor initContent={initContent} ref={content} />
			</S.Content>
			<S.SubmitButton type="button" onClick={handleUpdateButtonClick}>
				수정하기
			</S.SubmitButton>
		</S.Container>
	);
};

export default UpdateWriting;
