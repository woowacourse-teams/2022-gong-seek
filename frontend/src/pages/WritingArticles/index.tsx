import { postWritingArticle } from '@/api/article';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import styled from '@emotion/styled';
import { AxiosResponse, AxiosError } from 'axios';
import { useRef, useState } from 'react';
import { IoIosArrowDown } from 'react-icons/io';
import { useMutation } from 'react-query';
import { useNavigate, useParams } from 'react-router-dom';
import ToastUiEditor from './ToastUiEditor/ToastUiEditor';
import { Editor } from '@toast-ui/react-editor';

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

	if (isSuccess) {
		navigate(`/article/${category}?id=${data.data.id}`);
	}
	return (
		<Container>
			<SelectorBox>
				<PageLayout width="100%" height="fit-content">
					<TitleInput
						type="text"
						placeholder="제목을 입력해주세요"
						value={title}
						onChange={(e) => setTitle(e.target.value)}
					/>
				</PageLayout>
				<PageLayout width="100%" height="fit-content">
					<CategorySelectorBox>
						<CategorySelector
							name="writing"
							required
							value={categoryOption}
							onChange={(e) => setCategoryOption(e.target.value)}
						>
							<option value="" disabled>
								카테고리를 선택해주세요
							</option>
							<option value="error">에러</option>
							<option value="discussion">토론</option>
						</CategorySelector>
						<SelectorButton />
					</CategorySelectorBox>
				</PageLayout>
				<PageLayout width="100%" height="fit-content">
					<HashTagInput type="text" placeholder="해쉬태그를 입력해주세요" />
				</PageLayout>
			</SelectorBox>

			<Content>
				<ToastUiEditor ref={content} />
			</Content>
			<SubmitButton type="button" onClick={handleSubmitButtonClick}>
				등록하기
			</SubmitButton>
		</Container>
	);
};

const Container = styled.div`
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
`;

const Content = styled.div`
	width: 100%;
	margin-top: ${({ theme }) => theme.fonts.SIZE_028};
`;

const SelectorBox = styled.div`
	width: 90%;
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.fonts.SIZE_010};
`;

const TitleInput = styled.input`
	width: 100%;
	padding: 0.6rem 0.8rem;
	font-size: 0.8rem;
	border-style: none;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

const HashTagInput = styled.input`
	width: 100%;
	padding: 0.6rem 0.8rem;
	font-size: 0.8rem;
	border-style: none;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

const CategorySelectorBox = styled.div`
	position: relative;
	display: flex;
	align-items: center;
	width: 100%;
`;

const SelectorButton = styled(IoIosArrowDown)`
	position: absolute;
	border: none;
	font-size: 0.8rem;
	font-size: ${({ theme }) => theme.fonts.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};
	right: ${({ theme }) => theme.fonts.SIZE_004};
	pointer-events: none;
`;

const CategorySelector = styled.select`
	width: 100%;
	padding: 0.6rem 0.8rem;
	font-size: 0.8rem;
	border-color: transparent;
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	background-color: transparent;
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	&:focus {
		outline: none;
	}

	&:invalid {
		color: rgb(117, 117, 117);
	}
`;

const SubmitButton = styled.button`
	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	width: 90%;
	font-size: 0.8rem;
	padding: ${({ theme }) => theme.fonts.SIZE_004};
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	border-color: transparent;
	cursor: pointer;
	margin-top: ${({ theme }) => theme.fonts.SIZE_020};

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

export default WritingArticles;
