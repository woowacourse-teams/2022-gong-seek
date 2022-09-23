import { useEffect } from 'react';
import { useParams } from 'react-router-dom';

import { postImageUrlConverter } from '@/api/image';
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

	useEffect(() => {
		if (content.current) {
			content.current.getInstance().removeHook('addImageBlobHook');
			content.current.getInstance().addHook('addImageBlobHook', (blob, callback) => {
				(async () => {
					const formData = new FormData();

					formData.append('imageFile', blob);
					const url = await postImageUrlConverter(formData);
					callback(url, 'alt-text');
				})();
			});
		}
	}, [content]);

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
