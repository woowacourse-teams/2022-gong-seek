import { useParams } from 'react-router-dom';

import HashTag from '@/components/common/HashTag/HashTag';
import Loading from '@/components/common/Loading/Loading';
import ToastUiEditor from '@/components/common/ToastUiEditor/ToastUiEditor';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import usePostWritingArticle from '@/hooks/article/usePostUpdateWritingArticle';
import * as S from '@/pages/WritingArticles/index.styles';

const UpdateWriting = () => {
	const { id } = useParams();
	const { category } = useParams();

	if (id === undefined || category === undefined) {
		throw new Error('id와  category 값을 가지고 오지 못하였습니다');
	}

	const {
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
	} = usePostWritingArticle();

	if (isLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.SelectorBox>
				{/* 이 부분 WritingArticles와 동일, OptionBox의 여부만 다르다.*/}
				<PageLayout width="100%" height="fit-content">
					<S.TitleInput
						type="text"
						placeholder="제목을 입력해주세요"
						value={title}
						onChange={(e) => setTitle(e.target.value)}
						ref={titleInputRef}
					/>
				</PageLayout>
				{!isValidTitleInput && (
					<S.TitleInputErrorMsgBox>제목은 1글자 이상 500자 이하여야 합니다</S.TitleInputErrorMsgBox>
				)}
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
				<HashTag hashTags={hashTag} setHashTags={setHashTag} />
			</S.SelectorBox>

			<S.Content>
				<ToastUiEditor initContent={tempArticle.content} ref={content} />
			</S.Content>
			{/* 이부분만 다르다. */}
			<S.UpdateSubmitBox>
				<S.UpdateSubmitButton
					type="button"
					onClick={() => {
						handleUpdateButtonClick(id);
					}}
				>
					수정하기
				</S.UpdateSubmitButton>
			</S.UpdateSubmitBox>
		</S.Container>
	);
};

export default UpdateWriting;
