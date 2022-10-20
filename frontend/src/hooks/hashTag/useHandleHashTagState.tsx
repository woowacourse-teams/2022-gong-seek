import { useEffect, useState } from 'react';

import useGetAllHashTags from '@/hooks/hashTag/useGetAllHashTags';

const useHandleHashTagState = () => {
	const [targetHashTags, setTargetHashTags] = useState<{ name: string; isChecked: boolean }[]>([]);
	const [selectedHashTags, setSelectedHashTags] = useState<string[]>([]);
	const {
		data: tagsOption,
		isSuccess: isTagsOptionSuccess,
		isLoading: isTagsOptionLoading,
	} = useGetAllHashTags();

	useEffect(() => {
		if (isTagsOptionSuccess && tagsOption && tagsOption?.tag.length >= 1) {
			setTargetHashTags(
				tagsOption.tag.map((item) => ({
					name: item,
					isChecked: false,
				})),
			);
		}
	}, [isTagsOptionSuccess]);

	useEffect(() => {
		setSelectedHashTags(targetHashTags.filter((item) => item.isChecked).map((item) => item.name));
	}, [targetHashTags]);

	return {
		isTagsOptionLoading,
		isTagsOptionSuccess,
		targetHashTags,
		selectedHashTags,
		setTargetHashTags,
	};
};

export default useHandleHashTagState;
