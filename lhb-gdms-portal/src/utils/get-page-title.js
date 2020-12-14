import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Bin 博客平台'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
