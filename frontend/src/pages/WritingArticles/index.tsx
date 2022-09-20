import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import { postImageUrlConverter } from '@/api/image';
import AnonymouseCheckBox from '@/components/common/AnonymousCheckBox/AnonymouseCheckBox';
import HashTag from '@/components/common/HashTag/HashTag';
import Loading from '@/components/common/Loading/Loading';
import ToastUiEditor from '@/components/common/ToastUiEditor/ToastUiEditor';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import usePostWritingArticles from '@/hooks/article/usePostWritingArticles';
import useGetTempDetailArticles from '@/hooks/tempArticle/useGetTempDetailArticles';
import usePostTempArticle from '@/hooks/tempArticle/usePostTempArticle';
import * as S from '@/pages/WritingArticles/index.styles';

const WritingArticles = ({ tempId = '' }: { tempId?: '' | number }) => {
	const { category } = useParams();
	const [isAnonymous, setIsAnonymous] = useState<boolean>(false);
	const [tempArticleId, setTempArticleId] = useState<'' | number>(tempId);
	const [initContent, setInitContent] = useState<string>('');

	const {
		isLoading,
		content,
		handleSubmitButtonClick,
		title,
		isValidTitleInput,
		setTitle,
		titleInputRef,
		categoryOption,
		setCategoryOption,
		hashTags,
		setHashTags,
	} = usePostWritingArticles({ category, isAnonymous });

	const {
		saveTempArticleId,
		isSuccess: isTempArticleSavedSuccess,
		isError: isTempArticleSavedError,
		isLoading: isTempArticleSavedLoading,
	} = usePostTempArticle({ tempArticleId, setTempArticleId });

	const {
		data: tempArticleData,
		isLoading: isTempDetailArticleLoading,
		isSuccess: isTempDetailArticleSuccess,
		mutate: getTempDetailArticle,
	} = useGetTempDetailArticles({ tempArticleId });

	useEffect(() => {
		const timerInterval = setInterval(handleTempSavedButtonClick, 120000);

		return () => clearInterval(timerInterval);
	}, []);

	useEffect(() => {
		if (typeof tempArticleId === 'number') {
			getTempDetailArticle({ tempArticleId });
		}
	}, []);

	useEffect(() => {
		if (isTempDetailArticleSuccess && tempArticleData && tempArticleData.data) {
			setTitle(tempArticleData.data.title);
			setHashTags(tempArticleData.data.tag);
			setInitContent(tempArticleData.data.content);
			setIsAnonymous(tempArticleData.data.isAnonymous);
			setCategoryOption(tempArticleData.data.category);
		}
	}, [isTempDetailArticleSuccess]);

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

	if (isLoading) return <Loading />;

	//추후에 로딩 상태일 경우 안내메세지 추가하거나 애니메이션 추가하면 좋을 듯
	const handleTempSavedButtonClick = () => {
		if (content.current && category && titleInputRef.current) {
			saveTempArticleId({
				title: titleInputRef.current.value,
				category,
				tag: hashTags ? hashTags : [],
				isAnonymous,
				content: content.current?.getInstance().getMarkdown(),
			});
		}
	};

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
				<S.OptionBox>
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
								<option value="question">질문</option>
								<option value="discussion">토론</option>
							</S.CategorySelector>
							<S.SelectorButton />
						</S.CategorySelectorBox>
					</PageLayout>
					<HashTag hashTags={hashTags} setHashTags={setHashTags} />
				</S.OptionBox>
			</S.SelectorBox>
			<S.TemporaryStoreButtonBox>
				<S.TemporaryStoreButton onClick={handleTempSavedButtonClick}>
					임시저장
				</S.TemporaryStoreButton>
			</S.TemporaryStoreButtonBox>
			<S.Content>
				<ToastUiEditor initContent={initContent} ref={content} />
			</S.Content>
			<S.SubmitBox>
				<AnonymouseCheckBox setIsAnonymous={setIsAnonymous} />
				<S.SubmitButton type="button" onClick={() => handleSubmitButtonClick(categoryOption)}>
					등록하기
				</S.SubmitButton>
			</S.SubmitBox>
		</S.Container>
	);
};

export default WritingArticles;
