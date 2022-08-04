import { useParams } from 'react-router-dom';

import Loading from '@/components/common/Loading/Loading';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import usePostWritingArticle from '@/pages/UpdateWriting/hooks/usePostUpdateWritingArticle';
import ToastUiEditor from '@/pages/WritingArticles/ToastUiEditor/ToastUiEditor';
import * as S from '@/pages/WritingArticles/index.styles';

const UpdateWriting = () => {
	const { id } = useParams();
	const { category } = useParams();

	if (id === undefined || category === undefined) {
		throw new Error('id와  category 값을 가지고 오지 못하였습니다');
	}

	const { isLoading, title, setTitle, tempArticle, content, handleUpdateButtonClick } =
		usePostWritingArticle();

	if (isLoading) {
		return <Loading />;
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
						<S.CategorySelector name="writing" required value={category} disabled>
							<option value="" disabled>
								카테고리를 선택해주세요
							</option>
							<option value="question">질문</option>
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
				<ToastUiEditor initContent={tempArticle.content} ref={content} />
			</S.Content>
			<S.SubmitButton
				type="button"
				onClick={() => {
					handleUpdateButtonClick(id);
				}}
			>
				수정하기
			</S.SubmitButton>
		</S.Container>
	);
};

export default UpdateWriting;
